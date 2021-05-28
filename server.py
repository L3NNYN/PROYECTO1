from socket import *

def initServer():
    serversocket = socket(AF_INET, SOCK_STREAM)
    try:
        serversocket.bind(('localhost', 8080))
        serversocket.listen(4)
        while(True):
            (clientsocket, address) = serversocket.accept()

            rd = clientsocket.recv(4000).decode()
            pieces = rd.split("\n")
            if ( len(pieces) > 0 ) : print(pieces[0])

            data = "HTTP/1.1 200 OK\r\n"
            data += "Content-Type: text/html; charset=utf-8\n"
            data += "\r\n"

            f = open('views/inde2x.html', 'r')
            l = f.read()
            data += l

            clientsocket.sendall(data.encode())
            clientsocket.shutdown(SHUT_WR)
    except KeyboardInterrupt:
        print("\nApagando...\n");
    except Exception as exc :
        print("Error:\n");
        print(exc)

    serversocket.close()

print('Acceso http://localhost:8080')
initServer()