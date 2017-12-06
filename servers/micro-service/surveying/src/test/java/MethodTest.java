import java.util.UUID;

public class MethodTest {
    public static void main(String[] args) {
        String t = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9TWVNBRE1JTlJPTEVfU1lTQURNSU4iLCJ1bmlxdWVfbmFtZSI6InN5c3RlbSIsInVzZXJpZCI6ImZmODA4MDgxNjAyMGY1MWYwMTYwMjBmNTJkMWYwMDAwIiwiaXNzIjoicmVzdGFwaXVzZXIiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTUxMjU1NDc3MywibmJmIjoxNTEyMzgxOTczfQ.0olwy54hBy5vhtxN9Lv-e0NYcJ7zrUptK6zK1HTN7xY";
        String[] dd = t.split("\\.");
        System.out.print(dd.length);
    }
}
