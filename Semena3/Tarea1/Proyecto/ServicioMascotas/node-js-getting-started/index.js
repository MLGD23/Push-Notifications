var express = require('express');
var app = express();

app.set('port', (process.env.PORT || 5000));

//libreria para manipilar json
var bodyParser = require("body-parser");
//soporte para codificar json
app.use(bodyParser.json());
//Soporte para decodificar las url
app.use(bodyParser.urlencoded({ extended: true }));

//conexion base de datos firebase
var firebase = require("firebase");

firebase.initializeApp({
  serviceAccount: "mascotasApp2016-c5ded7789a0d.json",
  databaseURL: "https://mascotasapp2016-7210c.firebaseio.com"
});

var FCM = require('fcm-push');


app.use(express.static(__dirname + '/public'));

// views is directory for all template files
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');

app.get('/android', function(request, response) {
  response.render('pages/index');
});


//POST Funcion encargada de registrar
// el token de los dispositivos junto con la cuenta instagram.
//https://mascotasapp2016.herokuapp.com/registrar-usuario
//idDispositivo y idUsuarioInstagram
var registrarUsuarioURI = "registrar-usuario"
app.post("/" + registrarUsuarioURI, function(request, response){
	
	var idDispositivo = request.body.idDispositivo;
	var idUsuarioInstagram = request.body.idUsuarioInstagram;

	var db = firebase.database();
	var registrarUsuario = db.ref(registrarUsuarioURI).push();

	registrarUsuario.set({
		id_dispositivo : idDispositivo,
		id_usuario_instagram: idUsuarioInstagram
	});

	//https://mascotasapp2016.herokuapp.com/registrar-usuario/-KJlTaOQPwP-ssImryV1
	//se obtiene el id del json que contiene la parametrizacion del device
	var path = registrarUsuario.toString();
	var pathSplit = path.split(registrarUsuarioURI + "/")
	var idAutoGenerado = pathSplit[1];


	var respuesta = generarRespuestaServicioRegistro(db, idAutoGenerado);
	response.setHeader("Content-Type", "application/json");
	response.send(JSON.stringify(respuesta));	

})


//Funcion encargada de generar la respuesta cuando se registra el dispositivo
function generarRespuestaServicioRegistro(db,idAutoGenerado){

	var respuesta = {};
	var usuario = "";
	var ref = db.ref(registrarUsuarioURI);

	//se obtiene el id del ultimo registro ingresado
	ref.on("child_added", function(snapshot, prevChildKey) {
		device = snapshot.val();
		respuesta = {
			id: idAutoGenerado,
			idDispositivo: device.id_dispositivo,
			idUsuarioInstagram: device.id_usuario_instagram
		};
	});
	return respuesta;	
}


//https://mascotasapp2016.herokuapp.com/procesarLikeUsuario
var procesrLikeUsuarioURI = "procesarLikeUsuario"
app.post("/" + procesrLikeUsuarioURI, function(request, response){

	//Parametros de entrada para procesar el like
	var idUsuarioEmisor = request.body.idUsuarioEmisor;
	var idUsuarioReceptor = request.body.idUsuarioReceptor;
	var idFotoInstagram = request.body.idFotoInstagram;

	var respuesta = {};
	var db = firebase.database();
	var ref = db.ref("registrar-usuario");
	var encontro = false; 
	var notificacionProcesada = false;

	console.log("comienza");

	ref.orderByChild("id_usuario_instagram").equalTo(idUsuarioReceptor).on("child_added", function(snapshot) {
		encontro = true;
		usuario = snapshot.val();
		//registrar like bd firebase
		registraLikeMascota(usuario.id_dispositivo,idUsuarioReceptor,idFotoInstagram);

		//si el usuario receptor es el mismo emisor, no se envia notificacion
		if(idUsuarioEmisor != idUsuarioReceptor){
			//enviar notificación
			notificacionProcesada = enviarNotificaionLike(usuario.id_dispositivo,idUsuarioEmisor);
		}

		//Se procesa la respuesta
		respuesta = {
			idUsuarioNotificado: usuario.id_usuario_instagram,
			notificacionProcesada : notificacionProcesada
		};
		console.log("entra si encontro usuario");
		
	}, function (errorObject) {
		console.log("The read failed: " + errorObject.code);
		respuesta = {
			idUsuarioNotificado: "N/A",
			notificacionProcesada: false
		};		
	});

	if(encontro == false){
		console.log("No encontro ningun usuario");
		respuesta = {
			idUsuarioNotificado: "N/A",
			notificacionProcesada: false
		};		
	}	

	response.send(JSON.stringify(respuesta));

});

//Funcion encargada de registrar los likes
function registraLikeMascota(id_dispositivo,id_usuario_instagram,id_foto_instagram){
	var usuarioLikeFotoURI = "usuario-like-foto";
	var db = firebase.database();
	var usuarioLikeFoto = db.ref(usuarioLikeFotoURI).push();

	usuarioLikeFoto.set({
		id_dispositivo : id_dispositivo,
		id_usuario_instagram: id_usuario_instagram,
		id_foto_instagram : id_foto_instagram
	});
}

//Funcion encargada de enviar las notificaciones a los usuarios
function enviarNotificaionLike(tokenDestinatario, idUsuarioEmisor) {
	var mensaje = "El usuario " + idUsuarioEmisor + " ha dado like a una de tus fotos";
	var serverKey = 'AIzaSyBvLYFSkLLwFTZLJbvEAe2qxDV5EsAUeRg';
	var notificacionProcesada = false;
	var fcm = new FCM(serverKey);

	var message = {
	    to: tokenDestinatario, // required
	    collapse_key: '', 
	    data: {},
	    notification: {
	        title: 'Notificacion desde Servidor',
	        body: mensaje,
	        icon: "notificacion",
	        sound: "default",
	        color: "#00BCD4"
	    }
	};

	fcm.send(message, function(err, response){
	    if (err) {
	        console.log("Ocurrió un error el envio de la notificación");
	        notificacionProcesada = false;
	    } else {
	        console.log("Se envió la notificación exitosamente: ", response);
	        notificacionProcesada = true;
	    }
	});

	return notificacionProcesada;
}


app.listen(app.get('port'), function() {
  console.log('Node app is running on port', app.get('port'));
});




