import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.MalformedInputException;
import java.util.Iterator;

class IntSet {
  int[] buffer;
  IntSet() {
    // we represent integers as bits in integers, that is 2^32 bits
    // with 2 ^ 4 bits per integer it's 2^(32 - 4) integers
    buffer = new int[1 << 28];
  }

  void add(int value) {
    int segment = value >>> 4;
    int bit = value & 0b1111;
    buffer[segment] = buffer[segment] | (1 << bit);
  }

  long count() {
    long counter = 0;
    for (int i : buffer) {
      counter += Integer.bitCount(i);
    }
    return counter;
  }
}

public class IpCounter {
  // could be done faster, could have validation, but that's not the point of the task
  static int ipToInt(String ip) {
    String[] addrParts = ip.split("\\.");
    int result = 0;

    for (String s : addrParts) {
      result = (result << 8) + Integer.parseInt(s);
    }
    return result;
  }

  public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 0) {
      String filename = args[0];
      System.out.println("Counting IPs from " + filename);
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      IntSet set = new IntSet();
      for (Iterator<String> iter = reader.lines().iterator(); iter.hasNext(); ) {
        set.add(ipToInt(iter.next()));
      }
      System.out.println("IP count: " + set.count());
    } else {
      System.out.println("Please pass file with a list of IPs as the first argument!");
    }
  }
}
