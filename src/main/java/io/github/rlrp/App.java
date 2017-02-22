package io.github.rlrp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App
{
    public static void main(String[] args) throws IOException {
        //Path path = Paths.get("C:\\Projects\\Rlrp\\samples\\1CB5B12048B2375034255A9631B4F57D.replay");
        Path path = Paths.get("C:\\Projects\\Rlrp\\samples\\4BEEA68D48A5A2F2651BDD957D9ECF86.replay");
//        Path path = Paths.get("C:\\Projects\\Rlrp\\samples\\5F2FDD7C4557AA31982BE79BAA63373F.replay");
//        Path path = Paths.get("C:\\Projects\\Rlrp\\samples\\0DF914C6429E184A9BA22A9ADBE1E7BE.replay");
//        Path path = Paths.get("X:\\5F2FDD7C4557AA31982BE79BAA63373F.replay");
        byte[] data = Files.readAllBytes(path);
        ReplayStream stream = new ReplayStream(data);

        try {
            ReplayHeader header = ReplayHeader.deserialize(stream);
            System.out.println(header);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
