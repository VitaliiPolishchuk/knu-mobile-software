from flask import Flask, render_template, request, redirect, url_for, jsonify
app = Flask(__name__)

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from database_setup import Base, Artist, Gender

def initSession():
	engine = create_engine('sqlite:///musicdb.db')
	Base.metadata.bind = engine

	DBSession = sessionmaker(bind=engine)
	session = DBSession()
	return session

@app.route('/')
def home():
	return render_template('home.html')

@app.route('/gender')
def gender():
	session = initSession()
	items = session.query(Gender).all()
	return render_template('gender.html', items = items)
@app.route('/gender/new', methods=['GET', 'POST'])

def newGender():
    if request.method == 'POST':
        session = initSession()
        newItem = Gender(name=request.form['name'])
        session.add(newItem)
        session.commit()
        return redirect(url_for('gender'))
    else:
        return render_template('newgender.html')


@app.route('/gender/<int:gender_id>/edit', methods=['GET', 'POST'])
def editGender(gender_id):
    gender_id = int(gender_id)
    session = initSession()
    editedItem = session.query(Gender).filter_by(id=gender_id).one()
    print("success")
    if request.method == 'POST':	
        if request.form['name']:
            editedItem.name = request.form['name']
        session.add(editedItem)
        session.commit()
        return redirect(url_for('gender'))
    else:
        session.commit()
        return render_template('editgender.html', gender_id=gender_id, item=editedItem)

@app.route('/gender/<int:gender_id>/delete', methods=['GET', 'POST'])
def deleteGender(gender_id):
    session = initSession()
    itemToDelete = session.query(Gender).filter_by(id=gender_id).one()
    if request.method == 'POST':
        session.delete(itemToDelete)
        session.commit()
        return redirect(url_for('gender'))
    else:
        return render_template('deletegender.html', item=itemToDelete)


@app.route('/artist')
def artist():
	session = initSession()
	items = session.query(Artist).all()
	return render_template('artist.html', items = items)
@app.route('/artist/new', methods=['GET', 'POST'])

def newArtist():
    if request.method == 'POST':
        session = initSession()
        newItem = Artist(name=request.form['name'], gender = request.form['gender'])
        session.add(newItem)
        session.commit()
        return redirect(url_for('artist'))
    else:
        return render_template('newartist.html')


@app.route('/artist/<int:artist_id>/edit', methods=['GET', 'POST'])
def editArtist(artist_id):
    artist_id = int(artist_id)
    session = initSession()
    editedItem = session.query(Artist).filter_by(id=artist_id).one()
    print("success")
    if request.method == 'POST':	
        if request.form['name']:
            editedItem.name = request.form['name']
        if request.form['gender']:
            editedItem.gender = request.form['gender']
        session.add(editedItem)
        session.commit()
        return redirect(url_for('artist'))
    else:
        session.commit()
        return render_template('editartist.html', artist_id=artist_id, item=editedItem)

@app.route('/artist/<int:artist_id>/delete', methods=['GET', 'POST'])
def deleteArtist(artist_id):
    session = initSession()
    itemToDelete = session.query(Artist).filter_by(id=artist_id).one()
    if request.method == 'POST':
        session.delete(itemToDelete)
        session.commit()
        return redirect(url_for('artist'))
    else:
        return render_template('deleteartist.html', item=itemToDelete)

if __name__ == '__main__':
	app.debug = True
	app.run(host="127.0.0.1", port=2000)

