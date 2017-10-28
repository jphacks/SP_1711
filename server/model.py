import datetime
import setting as s
import sqlalchemy as sa
from sqlalchemy import Column, String, Integer, ForeignKey, create_engine, MetaData, DATETIME, func
from sqlalchemy.types import UserDefinedType
from sqlalchemy.orm import sessionmaker, relationship
from sqlalchemy.schema import UniqueConstraint
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class SessionFactory(object):

    def __init__(self, sql_connection, echo=False):
        self.engine = create_engine(sql_connection, encoding = 'utf-8', echo = True)
        Base.metadata.create_all(self.engine)

    def create(self):
        Session = sessionmaker(bind=self.engine)
        return Session()

class SessionContext(object):

    def __init__(self, session):
        self.session = session

    def __enter__(self):
        return self.session

    def __exit__(self, exc_type, exc_value, traceback):
        self.session.flush()
        self.session.commit()
        self.session.close()


class SessionContextFactory(object):

    def __init__(self, sql_connection, echo=False):
        self.session_factory = SessionFactory(sql_connection, echo)

    def create(self):
        return SessionContext(self.session_factory.create())

class Geometry(UserDefinedType):
    def get_col_spec(self, **kw):
        return 'GEOMETRY'

class User(Base):
    __tablename__ = 'users'
    __table_args__ = {'mysql_engine': 'InnoDB'}
    id = Column('id', Integer, primary_key = True, autoincrement = True)
    email = Column('email', String(255), unique = True)
    password = Column('password', String(255), nullable = False)
    log = relationship('Log')
    
    def __init__(self, email, password):
        self.email = email
        self.password = password

    def __repr__(self):
        return '<User>(email: {email}, password: {password})'\
                .format(email = self.email, password = self.password)

class Log(Base):
    __tablename__ = 'logs'
    __table_args__ = {'mysql_engine': 'InnoDB'}
    id = Column('id', Integer, primary_key = True, autoincrement = True)
    user_id = Column('user_id', Integer, ForeignKey('users.id'), onupdate = 'CASCADE') 
    location = Column('location', Geometry, nullable = False)
    created_at = Column('created_at', DATETIME, nullable = False)
    
    def __init__(self, user_id, location, created_at):
        self.user_id = user_id
        self.location = location
        self.created_at = created_at

    def __repr__(self):
        return '<Log>(user_id: {user_id}, location: {location}, created_at: {created_at})'\
                .format(user_id = self.user_id, location = self.location, created_at = self.created_at)

accessStr = 'mysql+pymysql://'\
            + s.mysqlUser   + ':'\
            + s.mysqlPass   + '@'\
            + s.mysqlHost   + ':'\
            + s.mysqlPort   + '/'\
            + s.mysqlDB     + '?charset=utf8'

sc_factory = SessionContextFactory(accessStr, True)

def pushUser(email, password):
    with sc_factory.create() as session:
        user = User(email, password)
        session.add(user)

def pushLog(user_id, lat, lng, created_at):
    with sc_factory.create() as session:
        location = func.ST_GeomFromText('POINT({x} {y})'\
                .format(x = lat, y = lng))
        log = Log(user_id, location, created_at)
        session.add(log)

if __name__ == '__main__':
    pushUser('hogehoge@oke.com', 'hogehoge')
    pushLog(1, 123.4, 214.6, '2017-08-17 12:12:12')
