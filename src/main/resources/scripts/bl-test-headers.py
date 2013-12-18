import csv
import sys
import os
import argparse

parser = argparse.ArgumentParser(description='Test the data in British Library TSV files to maintain consistency.')
parser.add_argument('filePath', help='Root file path to British Library TSV files')

def string_param(val):
    return val


def int_param(val):
    return safe_cast(val, int, -1)


def long_param(val):
    return safe_cast(val, long, -1L)


def safe_cast(val, to_type, default):
    return to_type(val)


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
                'BL_DLS_ID': string_param,
                'flickr_small_source': string_param,
                'flickr_small_height': int_param,
                'flickr_small_width': int_param,
                'flickr_medium_source': string_param,
                'flickr_medium_height': int_param,
                'flickr_medium_width': int_param,
                'flickr_large_source': string_param,
                'flickr_large_height': int_param,
                'flickr_large_width': int_param,
                'flickr_original_source': string_param,
                'flickr_original_height': int_param,
                'flickr_original_width': int_param}

for root, dirs, files in os.walk(ns.filePath):
    for file in files:
        if file.endswith('.tsv'):
            with open(os.path.join(root, file), 'rb') as tsvin:
                r = csv.reader(tsvin, delimiter='\t')
                header = r.next()

                try:
                    converters = [converters_map[c] for c in header]
                except KeyError as e:
                    print "There seems to be a column({1}) in file {0}".format(file, c)

                for row in r:
                    try:
                        {title:converter(value) for title, converter, value in zip(header, converters, row)}
                    except ValueError:
                        print "There seems to be some erroneous data({