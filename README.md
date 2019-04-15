# marketplace [![Build Status](https://travis-ci.org/dbiagi/marketplace.svg?branch=master)](https://travis-ci.org/dbiagi/marketplace)

## What is this?

A simple rest application using Spring Boot to build a marketplace application.

## Regras de negócios

Ao registrar é criado uma loja e um usuário que será o "dono" da loja
Esse usuário poderá cadastrar mais usuários "atendentes"
Cada atendente pode ou não cadastrar/atualizar/excluir usuários
Uma loja pode ter varios usuários, cada usuário atendente terá um custo X a mais na inscrição.

Haverá diferentes planos para adesao
A aplicação será somente via assinatura
A assinatura poderá ter renovação automática

Implementar vários gateways de pagamentos
