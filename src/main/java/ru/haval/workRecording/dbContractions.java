package ru.haval.workRecording;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class dbContractions {

  final public static Map<String, String> shops;
  static {
    Map<String, String> tmp = new HashMap<>();
    tmp.put("A", "Assembly");
    tmp.put("L", "Logistics");
    tmp.put("S", "Stamping");
    tmp.put("W", "Welding");
    tmp.put("J", "Jig");
    tmp.put("P", "Paint");
    shops = Collections.unmodifiableMap(tmp);
  }

}
