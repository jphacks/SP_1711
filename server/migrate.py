import setting as s
import sqlalchemy as sa

accessStr = 'mysql+pymysql://'\
            + s.mysqlUser   + ':'\
            + s.mysqlPass   + '@'\
            + s.mysqlHost   + ':'\
            + s.mysqlPort   + '/'\
            + s.mysqlDB     + '?charset=utf8'

engine = sa.create_engine(accessStr, echo=True)

sqlPath = 'sql/initialize.sql'
with open(sqlPath, 'r') as f:
    ins = f.read()
    engine.execute(ins)
