var BaseApiUrl = "http://localhost:5000/"; //ruta base a la API 
function apiURL(service) { //Función para formar la ruta completa a la API 
    return BaseApiUrl + service;
} window.onload = function () {
    var vm = new Vue({
        el: '#app',
        data: {
            color_s: '1',
            colores: [
                {class: '1', nombre:'Verde'}
            ],
            form:{
                usuario: ''
            }
        },
        mounted() {
            // this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            insertData() {
                axios.post(apiURL("insert_usuario"), JSON.stringify(this.form))
                .then((response) => { 
                    // this.$refs.modal.hide();

                    alertify.success(response.data);
                }).catch((error) => { alertify.error(`Ocurrió un problema al insertar. ${error}`); })
            },

        }
    });
}