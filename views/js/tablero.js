var BaseApiUrl = "http://localhost:5000/"; //ruta base a la API 
function apiURL(service) { //Función para formar la ruta completa a la API 
    return BaseApiUrl + service;
} window.onload = function () {
    var vm = new Vue({
        el: '#app',
        data: {
            jugadores:''
        },
        mounted() {
            this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            getData(){
                axios.get(apiURL("get_data/"))
                .then((response) => { 
                    // this.$refs.modal.hide();
                    this.jugadores = response.data;
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