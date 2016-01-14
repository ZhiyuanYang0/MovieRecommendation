import mysql.connector

genre_type_dict = {'Action': 1, 'Adult': 2, 'Adventure': 3, 'Animation': 4, 'Comedy': 5,
                   'Crime': 6, 'Documentary': 7, 'Drama': 8, 'Family': 9, 'Fantasy': 10,
                   'Film-Noir': 11, 'Horror': 12, 'Music': 13, 'Musical': 14, 'Mystery': 15,
                   'Romance': 16, 'Sci-Fi': 17, 'Short': 18, 'Thriller': 19, 'War': 20, 'Western': 21,
                   'Biography': 22, 'History': 23};

con_movie = mysql.connector.connect(user='heng', password='33333333',
                                    host='moviedb.cb47kibm2dah.us-west-2.rds.amazonaws.com',
                                    database='Movie')
con_movieDB = mysql.connector.connect(user='heng', password='33333333',
                                      host='moviedb.cb47kibm2dah.us-west-2.rds.amazonaws.com',
                                      database='MovieDB')
cursor_movie = con_movie.cursor()
cursor_movieDB = con_movieDB.cursor()


def fetch_one_assoc(cursor):
    data = cursor.fetchone()
    if data == None:
        return None
    desc = cursor.description
    dict = {}
    for (name, value) in zip(desc, data):
        dict[name[0]] = value
    return dict


def get_movie_id(name):
    query = ("SELECT MovieId FROM Movies "
             "WHERE Name = '%s'")
    cursor_movieDB.execute(query, (name))
    record = fetch_one_assoc(cursor_movieDB)
    if not record:
        return None
    movie_id = record['MovieId']
    return movie_id


def get_actor_id(first_name, last_name):
    if first_name == last_name:
        last_name = None
    if not last_name:
        query = ("SELECT ActorId FROM Actors "
                 "WHERE FirstName = \"%s\" AND LastName IS NULL;" % first_name)
        cursor_movieDB.execute(query)
    else:
        query = ("SELECT ActorId FROM Actors "
                 "WHERE FirstName = %s AND LastName = %s;")
        cursor_movieDB.execute(query, (first_name, last_name))

    record = fetch_one_assoc(cursor_movieDB)
    if not record:
        return None
    actor_id = record['ActorId']
    return actor_id


def get_director_id(first_name, last_name):
    if first_name == last_name:
        last_name = None
    if not last_name:
        query = ("SELECT DirectorId FROM Directors "
                 "WHERE FirstName = '%s' AND LastName IS NULL;" % first_name)
        cursor_movieDB.execute(query)
    else:
        query = ("SELECT DirectorId FROM Directors "
                 "WHERE FirstName = %s AND LastName = %s;")
        cursor_movieDB.execute(query, (first_name, last_name))

    record = fetch_one_assoc(cursor_movieDB)
    if not record:
        return None
    actor_id = record['DirectorId']
    return actor_id


def insert_movie(name, year, image_url, rating, description):
    if not rating:
        rating = '0.0'
    add_movie = ("INSERT INTO Movies "
                 "(Name, Year, ImageUrl, Rating, Description) "
                 "VALUES (%s, %s, %s, %s, %s)")
    cursor_movieDB.execute(add_movie, (name, str(year), image_url, str(rating), description))
    return cursor_movieDB.lastrowid


def insert_actor(height, weight, first_name, last_name, dob, profile, gender):
    add_actor = ("INSERT INTO Actors "
                 "(Height, Weight, FirstName, LastName, DoB, Profile, Gender) "
                 "VALUES (%s, %s, %s, %s, %s, %s, %s)")
    cursor_movieDB.execute(add_actor, (str(height), str(weight), first_name, last_name, dob, profile, gender))
    return cursor_movieDB.lastrowid


def insert_director(first_name, last_name, dob, profile, gender):
    add_director = ("INSERT INTO Directors "
                    "(FirstName, LastName, DoB, Profile, Gender) "
                    "VALUES (%s, %s, %s, %s, %s)")
    cursor_movieDB.execute(add_director, (first_name, last_name, dob, profile, gender))
    return cursor_movieDB.lastrowid


def insert_PerformedMovies(actor_id, movie_id):
    if not actor_id or not movie_id:
        return None
    add_PerformedMovies = ("INSERT INTO PerformedMovies "
                           "(ActorId, MovieId) "
                           "VALUES (%s, %s)" % (actor_id, movie_id))
    cursor_movieDB.execute(add_PerformedMovies)
    return cursor_movieDB.lastrowid


def insert_DirectedMovies(director_id, movie_id):
    if not director_id or not movie_id:
        return None
    add_DirectedMovies = ("INSERT INTO DirectedMovies "
                          "(DirectorId, MovieId) "
                          "VALUES (%s, %s)" % (director_id, movie_id))
    cursor_movieDB.execute(add_DirectedMovies)
    return cursor_movieDB.lastrowid


def insert_MovieGenres(movie_id, genre_id):
    if not movie_id or not genre_id:
        return None
    add_MovieGenres = ("INSERT INTO MovieGenres "
                       "(MovieId, GenreId) "
                       "VALUES (%s, %s)" % (movie_id, genre_id))
    cursor_movieDB.execute(add_MovieGenres)
    return cursor_movieDB.lastrowid


query_get_all = "SELECT * FROM MovieInfo ORDER BY Id LIMIT 50000 OFFSET 45792"
cursor_movie.execute(query_get_all)
record = fetch_one_assoc(cursor_movie)

count = 0
while record:
    count += 1
    print 'Original ID:', record['Id'], ';', record['Name'], '========================================================='
    if not record['Actors'] or record['Actors'] == '' or not record['Genre']:
        record = fetch_one_assoc(cursor_movie)
        continue
    if not get_movie_id(record['Name']):
        # inserts movie
        movie_id = insert_movie(record['Name'], record['Year'], record['ImageUrl'], record['Rating'],
                                record['Description'])
        print 'movie_id:', movie_id
        # inserts actors
        actor_list = record['Actors'].split(',')
        for actor_name in actor_list:
            if actor_name:
                names = actor_name.split(' ')
                actor_id = get_actor_id(names[0], names[-1])
                if not actor_id:
                    actor_id = insert_actor(0, 0, names[0], names[-1], '', '', '')
                insert_PerformedMovies(actor_id, movie_id)
                print 'actor_id:', actor_id, '; actor_name:', actor_name
        # inserts director
        if record['Director']:
            names = record['Director'].split(' ')
            direct_id = get_director_id(names[0], names[-1])
            if not direct_id:
                direct_id = insert_director(names[0], names[-1], '', '', '')
            insert_DirectedMovies(direct_id, movie_id)
            print 'direct_id:', direct_id, '; director_name:', record['Director']
        # inserts genre
        genre_list = record['Genre'].split(' | ')
        for genre in genre_list:
            if not genre in genre_type_dict:
                continue
            genre_id = genre_type_dict[genre]
            insert_MovieGenres(movie_id, genre_id)
            print 'genre_id:', genre_id, '; genre_name:', genre
    record = fetch_one_assoc(cursor_movie)
    if count % 10 == 0:
        con_movieDB.commit()

con_movieDB.commit()

cursor_movie.close()
cursor_movieDB.close()
con_movie.close()
con_movieDB.close()

