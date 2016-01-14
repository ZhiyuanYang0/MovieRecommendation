from bs4 import BeautifulSoup
import urllib2
import mysql.connector

def get_page(url):
    response = urllib2.urlopen(url)
    html = response.read()
    return html

con = mysql.connector.connect(user='sam', password='********',
                              host='moviedb.cb47kibm2dah.us-west-2.rds.amazonaws.com',
                              database='Movie')
cursor = con.cursor()

add_movie = ("INSERT INTO MovieInfo "
              "(Name, Year, ImageUrl, Rating, Description, Director, Actors, Genre) "
              "VALUES (%(Name)s, %(Year)s, %(ImageUrl)s, %(Rating)s, %(Description)s, %(Director)s, %(Actors)s, %(Genre)s)")

base_url = 'http://www.imdb.com/search/title?sort=moviemeter,asc&title_type=feature&year=1874,2015&start='

for start in range(1, 303998, 50):
    url = base_url + str(start)
    print 'start fetching', url
    re = get_page(url)
    soup = BeautifulSoup(re, "html.parser")

    for detail in soup.findAll('tr', {'class' : 'even detailed'}):
        movie_image_url = detail.find('td', {'class' : 'image'}).a.img['src']
        content = detail.find('td', {'class' : 'title'})
        movie_name = content.a.get_text()
        movie_year = content.find('span', {'class' : 'year_type'}).get_text()[1:5]
        movie_rating = None
        try:
            movie_rating = content.find('div', {'class' : 'user_rating'}).div['id'].split('|')[2]
        except:
            None
        movie_description = None
        try:
            movie_description = content.find('span', {'class' : 'outline'}).get_text()
        except:
            None
        movie_director = None
        try:
            movie_director = content.find('span', {'class' : 'credit'}).find('a').get_text()
        except:
            None
        movie_actors = None
        movie_genre = None
        try:
            movie_actors = ','.join([ele.get_text() for ele in content.find('span', {'class' : 'credit'}).find_all('a')[1:]])
        except:
            None
        try:
            movie_genre = content.find('span', {'class' : 'genre'}).get_text()
        except:
            None

        data_movie = {
          'Name': movie_name,
          'Year': movie_year,
          'ImageUrl': movie_image_url,
          'Rating': movie_rating,
          'Description': movie_description,
          'Director': movie_director,
          'Actors': movie_actors,
          'Genre': movie_genre,
        }
        cursor.execute(add_movie, data_movie)
    con.commit()
    print 'committed successfully\n'

# close the cursor and connection
cursor.close()
con.close()
