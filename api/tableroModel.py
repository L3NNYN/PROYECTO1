jugadores = []

def insertarJugador(usuario):
    if usuario in jugadores:
        return "N"
    else:
        jugadores.append(usuario)

def getJugadores():
    return jugadores

