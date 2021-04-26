Este es un archivo para incluir informacion general, tipo manual de uso para el proyecto.

******************************************
Pasos y comandos básicos Git

Para obtener el proyecto:

git clone https://github.com/L3NNYN/PROYECTO1

Publicar los cambios realizados:

1) git add . 
(agrega los archivos nuevos y en los que se hayan hechos cambios, las carpetas vacias no se agregan)

2) git commit -m "mensaje" 
(sin mensaje no hay commit, se preparan los archivos agregados en el paso anterior, es bueno poner en el mensaje de manera breve cambios realizados)

3) git push (se publica el commit)

Obtener cambios:

git pull 
(obtiene cambios, si hay archivos modificados de manera local sin publicar puede generar conflictos si otro compañero modificó el mismo archivo y este publicó sus cambios antes)

Otros: 

git status
(Este comando es muy útil para ver el estado local del proyecto, podemos ver si hay archivos modificados sin agregar, archivos agregados para commit, commits en cola, etc. Se puede usar en cualquier momento y no afecta el flujo de comandos hechos previamente.)

******************************************