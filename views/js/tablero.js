var BaseApiUrl = "http://localhost:5000/"; //ruta base a la API 

function getJugador(name, jugadores){
    for(let i =0; i < jugadores.length; i++){
        if(name == jugadores[i].nombre) {
            return jugadores[i];
        }
    }
}


function apiURL(service) { //Función para formar la ruta completa a la API 
    return BaseApiUrl + service;
} window.onload = function () {
    var vm = new Vue({
        el: '#app',
        data: {
            jugador:'',
            color: '',
            fecha: '',
            jugadores:''
        },
        mounted() {
            this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            getData(){
                axios.get(apiURL("get_data/"))
                .then((response) => {

                    var name = window.localStorage.getItem('miName');

                    var aux = {};
                    aux = getJugador(name, response.data); //Retorna el jugador con el que se ingreso 
                    this.jugador = aux.nombre;
                    this.color = aux.color;
                    this.fecha = aux.fecha;

                    alertify.success('Has ingresado como ' + this.jugador);
                    this.jugadores = response.data;
                    // window.Location.href = '';
                }).catch((error) => { alertify.error(`Ocurrió un problema al obtener los datos. ${error}`); })
            },

            insertData() {
                axios.post(apiURL("insert_usuario"), JSON.stringify(this.form))
                .then((response) => { 
                    alertify.success(response.data);
                }).catch((error) => { alertify.error(`Ocurrió un problema al insertar. ${error}`); })
            },

            save(evt) {

            },

            reset(){

            }

        }
    });
}