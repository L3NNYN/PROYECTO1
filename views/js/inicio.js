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
                {class: '1', nombre:'Verde'},
                {class: '2', nombre:'Azul'},
                {class: '3', nombre:'Amarillo'},
                {class: '4', nombre:'Rojo'},
                {class: '5', nombre:'Violeta'},
                {class: '6', nombre:'Cyan'},
                {class: '7', nombre:'Otro'}
            ],
            form:{
                nombre: ''
            }
        },
        mounted() {
            // this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            insertData() {
                // localStorage.removeItem("key");
                window.localStorage.setItem('miName', this.form.nombre);
                axios.post(apiURL("insert_jugador"), JSON.stringify(this.form))
                .then((response) => { 
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