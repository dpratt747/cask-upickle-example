## Example requests:

```bash
curl --location 'http://127.0.0.1:8080/user' \
--header 'Content-Type: application/json' \
--data '{
 "user_id": "f066062b-b857-423c-bf0d-05ce9d2dd848",
 "name": "Tyshawn.Hartmann46"
}'
```

```bash
curl --location --request GET 'http://127.0.0.1:8080/users' \
--header 'Content-Type: application/json'
```

```bash
curl --location --request GET 'http://127.0.0.1:8080/user/f066062b-b857-423c-bf0d-05ce9d2dd848' \
--header 'Content-Type: application/json'
```