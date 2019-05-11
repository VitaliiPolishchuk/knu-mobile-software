import os
import sys
from sqlalchemy import Column, ForeignKey, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import create_engine

Base = declarative_base()


class Gender(Base):
    __tablename__ = 'gender'

    id = Column(Integer, primary_key=True)
    name = Column(String(250), nullable=False)

    @property
    def serialize(self):
        """Return object data in easily serializeable format"""
        return {
            'name': self.name,
            'id': self.id,
        }


class Artist(Base):
    __tablename__ = 'artist'

    name = Column(String(80), nullable=False)
    id = Column(Integer, primary_key=True)
    gender = Column(Integer)

    @property
    def serialize(self):
        """Return object data in easily serializeable format"""
        return {
            'name': self.name,
            'id': self.id,
            'gender': self.gender
        }


engine = create_engine('sqlite:///musicdb.db')


Base.metadata.create_all(engine)
