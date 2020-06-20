## Step 07 - A Quick Review - JDBC vs Spring JDBC

In the previous step, we created and executed the findAll method. We were able to print all the details of a Person. We saw how easy it is to write a simple method with Spring JDBC. This *JdbcTemplate jdbcTemplate;* is something which is implemented in Spring JDBC. You can see that it's part of org.springframework.jcbc.core. How did we do this kind of stuff in JDBC? It was very very complex.

```java
@Repository
public class PersonJdbcDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> findAll() {
        return jdbcTemplate.query("select * from person", 
                new BeanPropertyRowMapper(Person.class));
    }
}
```

In this step, let's compare JDBC with Spring JDBC and understand how Spring JDBC makes it very easy. So, what you're looking below is a simple JDBC example for doing a similar exercise. 

```java
    @Override
    public List<Todo> retrieveTodos(String user)
            throws SQLException {
        Connection connection = datasource.getConnection();

        PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM TODO where user=?");

        st.setString(1, user);

        ResultSet resultSet = st.executeQuery();
        List<Todo> todos = new ArrayList<>();

        while (resultSet.next()) {

            Todo todo = new Todo(resultSet.getInt("id"),
                    resultSet.getString("user"),
                    resultSet.getString("desc"),
                    resultSet.getTimestamp("target_date") ,
                    resultSet.getBoolean("is_done"));
            todos.add(todo);
        }

        #1
        st.close();
        connection.close();

        return todos;

    }
```

So instead of the retrieve Persons *List<Person> findAll();*, here it is retrie TODOs *public List<Todo> retrieveTodos(String user);*. So, in this example we are using JDBC. In JDBC what we needed to do was, first we needed to get a connection *Connection connection = datasource.getConnection();*, second we needed to create a prepared statement *PreparedStatement st = connection.prepareStatement("SELECT * FROM TODO where user=?");*, third you need to execute the query *ResultSet resultSet = st.executeQuery();*, and four we needed to get the values individually out of the resultSet and added to the list. Later, we need to close everything out *st.close();* and return the TODOs. 

One of the problems which are not handled very well in this piece of code is exception handling, because if there is some piece of code here *resultSet.getX("")*, fires an exception, then this statement and the connection will not get closed (see #1), and there will be a leak of our connection. So you'll lose that connection, and that's not really good. So if I really implement that well, then all this code saw in this example becomes even more complex.

This is how typically things I'd done in JDBC. Spring JDBC provides an easier option than that. Let's consider the advantage of Spring JDBC. One is less amount of code. Instead of writing a hundred lines, you'd be writing five or ten lines of code. Number two it also prevents you from making mistakes, because what we'll do there is we don't really need to handle the connection statement and things like that. And even if there is an exception happening the JDBC template makes sure that the connection is closed. 

So the number of chances that you make a mistake is reduced when you use Spring JDBC. So those are the two main advantages with respect to Spring JDBC. In the step, we took a quick look at JDBC, some example JDBC code and compared it with how things are done with Spring JDBC. 