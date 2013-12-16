import csv
import pymongo
from pymongo import MongoClient
import sys
import os
import argparse
import timing
import bson

parser = argparse.ArgumentParser(description='Import meta-data from the British Library into mongodb.')
parser.add_argument('--mongoHost', default='localhost', required=False, help='Mongo host name.')
parser.add_argument('--mongoPort', default=27017, required=False, help='Mongo port number.', type=int)
parser.add_argument('--mongoDb', default='bl_import', required=False, help='Mongo database name.')
parser.add_argument('--mongoCollection', default='bl_meta', required=False, help='Mongo collection name.')
parser.add_argument('--insertSize', default=50, required=False, help='Size of bulk import.', type=int)
parser.add_argument('--insertSafe', default=True, required=False, help='Set false to use fire-and-forget insert.', type=bool)
parser.add_argument('--mongoUser', default="", required=False, help='Mongo user.')
parser.add_argument('--mongoPass', default="", required=False, help='Mongo pass.')
parser.add_argument('filePath', help='Root file path to British Library TSV files')
ns = parser.parse_args()

wVal = 1
if (ns.insertSafe == False):
    wVal = 0

client = MongoClient(ns.mongoHost, ns.mongoPort)

db = client[ns.mongoDb]
if (len(ns.mongoUser) > 0):
    db.authenticate(ns.mongoUser, ns.mongoPass)
collection = db[ns.mongoCollection]
bulkDocuments = []


def string_param(val):
    return val


def int_param(val):
    return safe_cast(val, int, -1)


def long_param(val):
    return safe_cast(val, long, -1L)


def safe_cast(val, to_type, default):
    try:
        return to_type(val)
    except ValueError:
        return default


converters_map = {'flickr_id': long_param,
                'flickr_url': string_param,
                'book_identifier': long_param,
                'title': string_param,
                'first_author': string_param,
                'pubplace': string_param,
                'publisher': string_param,
                'date': string_param,
                'volume': int_param,
                'page': int_param,
                'image_idx': int_param,
                'ARK_id_of_book': string_param,
                'BL_DLS_ID': string_param}

for root, dirs, files in os.walk(ns.filePath):
    for file in files:
        if file.endswith('.tsv'):
            with open(os.path.join(root, file), 'rb') as tsvin:
                r = csv.reader(tsvin, delimiter='\t')
                header = r.next()

                try:
                    converters = [converters_map[c] for c in header]
                except KeyError as e:
                    print "Key error processing file {1} -> ({0})".format(e, file)

                for row in r:
                    row = {title:converter(value) for title, converter, value in zip(header, converters, row)}
                    row['fileName'] = file
                    bulkDocuments.append(row)
                    if (len(bulkDocuments) > ns.insertSize):
                        try:
                            collection.insert(bulkDocuments, w = wVal)
                            bulkDocuments = []
                        except bson.errors.InvalidDocument as id:
                            print "Error inserting document from file {0}, most recent row: {1} -> {2}".format(file, row, id)


if (len(bulkDocuments) > 0):
    collection.insert(bulkDocuments, w = wVal)
    bulkDocuments = []

collection.create_index("flickr_id")
collection.create_index("title")
collection.create_index("fileName")