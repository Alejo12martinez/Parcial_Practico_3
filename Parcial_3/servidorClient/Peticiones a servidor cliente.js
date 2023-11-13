
PARA CREAR LIBROS -- POSTMAN
{
  "method": "POST",
  "endpoint": "/books",
  "data": {
    "title": "Harry Potter",
    "description": "La piedra Filosofal",
    "author": "JK Rowling"
  }
}




PARA ACTUALIZAR -- POSTMAN

{
  "method": "PUT",
  "endpoint": "/books/16",
  "data": {
    "title": "Pruebas 2",
    "description": "Libro de pruebas",
    "author": "Autor de Pruebas"
  }
}




PARA BORRAR -- POSTMAN

{
  "method": "DELETE",
  "endpoint": "/books/1"
}



PARA OBTENER LIBROS -- POSTMAN

{
  "method": "GET",
  "endpoint": "/books"
}




-- CURL

curl -X POST "http://192.168.60.4:5001/make_request" -H "Content-Type: application/json" -d "{\"method\": \"POST\", \"endpoint\": \"/books\", \"data\": {\"title\": \"Harry Potter\", \"description\": \"La piedra Filosofal\", \"author\": \"JK Rowling\"}}"


curl -X PUT "http://192.168.60.4:5001/make_request" -H "Content-Type: application/json" -d "{\"method\": \"PUT\", \"endpoint\": \"/books/4\", \"data\": {\"title\": \"Cien Años de Soledad\", \"description\": \"Libro Colombiano\", \"author\": \"Gabriel Garcia Marquez\"}}"


curl -X GET "http://192.168.60.4:5001/make_request" -H "Content-Type: application/json" -d "{\"method\": \"GET\", \"endpoint\": \"/books\"}"


curl -X GET "http://192.168.60.4:5001/make_request" -H "Content-Type: application/json" -d "{\"method\": \"GET\", \"endpoint\": \"/books/1\"}"


curl -X DELETE "http://192.168.60.4:5001/make_request" -H "Content-Type: application/json" -d "{\"method\": \"DELETE\", \"endpoint\": \"/books/1\"}"


