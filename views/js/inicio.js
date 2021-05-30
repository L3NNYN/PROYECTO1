var BaseApiUrl = "http://localhost:5000/"; //ruta base a la API 

function apiURL(service) { //Función para formar la ruta completa a la API 
    return BaseApiUrl + service;
} window.onload = function () {
    
    var vm = new Vue({
        el: '#app',
        data: {
            nombre: 'perrou',
            fecha: '2021-29-5',
            color: '5',
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
                nombre: 'perrou',
                fecha: '2021-29-5',
                color: '5'
            }
        },
        mounted() {
            // this.getData(); // Carga los datos desde el inicio (se creará más adelante) 
        }, methods: { //Aquí van las funciones VUE 

            insertData() {
                // localStorage.removeItem("key");

                //Se guarda el nombre del jugador como una variable global para poder recuperarla en la vista del tablero
                window.localStorage.setItem('miNombre', this.form.nombre);

                //Se ingresa en el servidor la informacion que haya ingresado, en formato json
                axios.post(apiURL("insert_jugador"), JSON.stringify(this.form))
                .then((response) => { 

                    alertify.success(response.data);
                }).catch((error) => { alertify.error(`Ocurrió un problema al insertar. ${error}`); })
            },


            save(evt) {
                if(this.form.nombre != ''){
                    this.insertData();  
                }
            },

            reset(evt){

            }

        }
    });
}