#!/bin/bash
# Generate self-signed certificate and key for HTTPS

openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes \
  -subj "/C=US/ST=State/L=City/O=Organization/CN=172.20.1.21" \
  -addext "subjectAltName=IP:172.20.1.21"
