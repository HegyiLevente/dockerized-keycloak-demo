## Usage

Run the following command to start services

`docker-compose up`

Run the following command to stop services

`docker-compose down`

## Postman Set Up and accessing the endpoints

Accessing `localhost:8000/test/hello` with the `Authorization Type` of `Inherit auth from parent` should give you a simple message as result with status `200 OK` since that endpoint is not secured at all.

Accessing `localhost:8000/test/user` with same `Authorization Type` as above should give you a `401 Unauthorized` status code since that endpoint is only accessible for users of the application with the role of USER, so you need a bearer token for that one. Being an all containerized demo here's what you need to do:

Run the application. After everything is upp run `docker ps` it should display all the running containers. Enter in the bash of the keycloak container.

In my case the command should look like this: 

```sh
docker exec -it demo_keycloak_1 /bin/sh
```

After you entered, you need to run a cURL post request to get the token with which you can acces the endpoint:

```sh
curl -X POST '<KEYCLOAK_SERVER_URL>/auth/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'client_secret=<CLIENT_SECRET>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'
```

In my case it would look like this:

```sh
curl -X POST 'http://keycloak:8080/auth/realms/Demo-Realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=springboot-microservice' \
--data-urlencode 'client_secret=203ada5e-4480-48a6-8b6c-d27372233b45' \
--data-urlencode 'username=user' \
--data-urlencode 'password=user'
```

> **NOTE**: I will explain how this cURL command is built up below so you'll know how to get the corresponding data in your case.

Running that command should give you the `access_token`. Copy it, go back to PostMan, set the `Authorization Type` to `Bearer Token` and paste the access token you got from the cURL command. And here you go, you should see the message. You can test the other endpoints with the same steps but changing the credentials for the admin users. So the last two entries from the cURL command would look like `--data-urlencode 'username=admin'` and `--data-urlencode 'password=admin'`. As you can see we just changed the credentials. 

> **NOTE**: The access tokens are allive for an hour so you don't need to execute the obtaining process for every request. Also they are available that much time because I modified it on the keycloak server, by default they are up for 5 minutes.

## How you can obtain the data for the cURL command

The keycloak server is running on `localhost:8080`. You can look up for the credentials in the `keycloak.env` file. After you logged in, go to `Clients` tab, here you can get the `<CLIENT_ID>` which in our case is `springboot-microservice`. Select it also, so you enter in the client config where you can get the `<CLIENT_SECRET>` by selecting the `Credentials` tab where you can find it.

The users with the ADMIN and USER roles are made by myself and you already know the credentials for them.


