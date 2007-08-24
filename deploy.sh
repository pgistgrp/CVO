#!/bin/sh

ant createdb
./LIT.sh
ant loadTaxData
ant staticpage

