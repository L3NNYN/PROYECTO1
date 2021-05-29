var BaseApiUrl = "http://localhost:5000/"; //ruta base a la API 
function apiURL(service) { //Función para formar la ruta completa a la API 
    return BaseApiUrl + service;
} window.onload = function () {
    var vm = new Vue({
        el: '#app',
        data: {
            fecha: '',
            color: '1',
            colores: [
                {class: '1', nombre:'Verde'}
            ],
            form:{
                nombre: ''
            }
        },
        mounted() {
            // this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            insertData() {
                axios.post(apiURL("insert_jugador"), JSON.stringify(this.form))
                .then((response) => { 
                    // this.$refs.modal.hide();
                    console.log(responde.data)
                    alertify.success(response.data);
                }).catch((error) => { alertify.error(`Ocurrió un problema al insertar. ${error}`); })
            },


            save(evt) {
                this.insertData();  
            },

            reset(evt){

            }

        }
    });
}