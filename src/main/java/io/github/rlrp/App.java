package io.github.rlrp;

import io.github.rlrp.binaryreader.BinaryInteger32;
import io.github.rlrp.binaryreader.BinaryInteger8;
import io.github.rlrp.binaryreader.BinaryString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App
{
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("X:\\1CB5B12048B2375034255A9631B4F57D.replay");
        //Path path = Paths.get("C:\\Projects\\Rlrp\\samples\\5F2FDD7C4557AA31982BE79BAA63373F.replay");
        byte[] data = Files.readAllBytes(path);

        ReplayHeader header = ReplayHeader.deserialize(data);
//
//        for(int i = 0; i < data.length - 1; i++) {
//            BinaryString value = new BinaryString(data, i);
//            int byteCount = value.getByteBuffer().array().length;
//            i += byteCount;
//            String s = value.read();
//            if(s.length() < 3) {
//                continue;
//            }
//            System.out.println("Offset " + (i - (byteCount + 1)) + " String: " + s);
//            if(s.equals("IntProperty")) {
//                i += 1; // Move to next byte
//                // Skip
//                // Datalength (4 byte int)
//                // Unknown 4 bytes
//                i += 8;
//                System.out.println(new BinaryInteger32(data, i).toString());
////                System.out.println(new BinaryInteger8(data, i).toString());
////                System.out.println(new BinaryInteger32(data, i + 4).toString());
////                System.out.println(new BinaryInteger8(data, i + 4).toString());
////                System.out.println(new BinaryInteger32(data, i + 8).toString());
////                System.out.println(new BinaryInteger8(data, i + 8).toString());
//            }
//
//            break;
//        }

    }


}
