package com.orientechnologies.tinkerpop;

import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraphFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.InputStream;

/**
 * Created by Enrico Risa on 26/01/17.
 */
public abstract class AbstractRemoteTest {


  protected OrientGraphFactory factory;
  @Rule
  public TestName name = new TestName();

  protected static final String SERVER_DIRECTORY = "./target";
  private OServer server;

  @Before
  public void setup() throws Exception {

    System.setProperty("ORIENTDB_HOME", SERVER_DIRECTORY);

    InputStream stream = ClassLoader.getSystemResourceAsStream("orientdb-server-config.xml");
    server = OServerMain.create(false);
    server.startup(stream);
    server.activate();

    server.createDatabase(name.getMethodName(), ODatabaseType.MEMORY, OrientDBConfig.defaultConfig());

    factory = new OrientGraphFactory("remote:localhost/" + name.getMethodName());
  }

  @After
  public void teardown() {
    factory.close();
    server.shutdown();
  }

}
