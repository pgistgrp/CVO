#!/bin/sh

ant createdb
./LIT.sh
ant loadTaxData
ant staticpage

echo "Now don't forget to run script doAll.sql"
