import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;

class InnerHello {
    public String name;

    public static String getUserOnboarding(String userOnboarding){
        return "Useronboarding";
    }
    

}

class UserData {
    public UserData(String userId){
        this.userId = userId;
    }
    public String userId;
}

interface IUser {
    UserData getUserData();
}

class SampleThread extends Thread {
    public void run(HashMap<Object,String> hashMap) {
        System.out.println("Thread running...");
    }
}

public class Hello implements IUser {
    public static void main(String[] args) {
        String filePath = "/Users/macos/Documents/kit/Hello/src/test.json";
        InnerHello world = new InnerHello();
        InnerHello.getUserOnboarding(filePath);
        Thread thread = new SampleThread();
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("Testing" , "HiHi");
        System.out.println(hashMap.toString());
        thread.start();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String absolutePth = Paths.get("").toAbsolutePath().toString();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            LocalDate localDate = LocalDate.now();
            System.out.println(absolutePth);
            System.out.println(world.name);
            System.out.println(localDate.toString());
            // getUserOnboarding("Nme");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserData getUserData() {
        throw new UnsupportedOperationException("Unimplemented method 'getUserData'");
    }

  
}

