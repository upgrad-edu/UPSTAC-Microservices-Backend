package org.upgrad.upstac.config.loaddata;

import org.upgrad.upstac.auth.register.RegisterRequest;
import org.upgrad.upstac.users.models.Gender;

import java.time.LocalDate;
import java.util.*;

import static org.upgrad.upstac.shared.DateParser.getStringFromDate;

public class DataGenerator {

    public static final String PASSWORD = "password";
    static List<String> generatedPhones = new ArrayList<>();


    static void initDataGenerator(){
        generatedPhones = new ArrayList<>();
    }
    static boolean canSetOtherGender() {
        Random r = new Random();
        int low = 1;
        int high = 100;
        int result = r.nextInt(high - low) + low;
        return result == 8;
    }

    private static Gender getRandomGender() {

        List<Gender> genders = Arrays.asList(Gender.MALE, Gender.FEMALE);

        Random rand = new Random();
        final Gender gender = genders.get(rand.nextInt(genders.size()));

        if (gender.equals(Gender.FEMALE) && canSetOtherGender())
            return Gender.OTHER;
        else
            return gender;
    }
    static String getRandomDoorNumber() {

        int min = 1;
        int max = 275;

        Random r = new Random();
        int l = (max - min) + 1;
        int res = r.nextInt(l) + min;

        String myChar = getRandomItemInStrings(Arrays.asList("", "A", "B", "C", "D", "E", "F", "G", "H"));


        if (myChar.equalsIgnoreCase(""))
            return "" + res;
        else
            return res + "/" + myChar;
    }

    static String getRandomItemInStrings(List<String> characters) {
        Random rand = new Random();
        return characters.get(rand.nextInt(characters.size()));
    }

    private static String getRandomStreetName() {

        String street = getRandomItemInStrings(Arrays.asList("Gandhi Street", "Nehru Street", "Nietzche Avenue", "Jefferson Avenue", "Grant Avenue", "Franklin Avenue", "4th Street North", "8th Street", "Strawberry Lane", "Sycamore Street", "Monroe Drive", "Route 10", "Highland Avenue", "Sherman Street", "Mulberry Court", "Main Street North", "Hillside Drive", "Andover Court", "Eagle Street", "Lakeview Drive", "Mulberry Lane", "Route 6", "Lantern Lane", "5th Street", "Buttonwood Drive", "Colonial Avenue", "Ann Street", "Brookside Drive", "Canterbury Court", "Marshall Street", "Hudson Street", "Union Street", "Jones Street", "Sycamore Lane", "6th Avenue", "Clark Street", "Cedar Avenue", "Park Street", "Evergreen Lane", "Aspen Drive", "6th Street North", "Magnolia Avenue", "Heather Lane", "Spruce Avenue", "B Street", "Ashley Court", "York Street", "Front Street North", "Brook Lane", "Jackson Avenue", "Hillcrest Drive", "Windsor Drive", "2nd Street"));

        return street;

    }

     static String getRandomAddress(Integer pinCode) {


        Map<Integer, String> addresses = new HashMap<>();
        addresses.put(110001, getRandomDoorNumber() + " - " + getRandomStreetName() + ",New Delhi");
        addresses.put(560003, getRandomDoorNumber() + " - " + getRandomStreetName() + ",Bangalore");
        addresses.put(400001, getRandomDoorNumber() + " - " + getRandomStreetName() + ",Mumbai");
        addresses.put(700004, getRandomDoorNumber() + " - " + getRandomStreetName() + ",KOlkatta");


        if (addresses.containsKey(pinCode))
            return addresses.get(pinCode);
        else
            return getRandomDoorNumber() + " - " + getRandomStreetName() + ",Goa";

    }
     static int getRandomPinCode() {


        List<Integer> integers = Arrays.asList(110001, 560003, 400001, 700004);
        Random rand = new Random();
        return integers.get(rand.nextInt(integers.size()));

    }


    private static String getRandomPhoneNumber() {

        Long start = 9629150000L;

        int min = 1000;
        int max = 9999;

        Random r = new Random();
        int l = (max - min) + 1;
        int res = r.nextInt(l) + min;
        long l1 = start + res;
        return "" + l1;
    }

    public static Integer getRandomAge() {

        int min = 21;
        int max = 75;

        Random r = new Random();
        int l = (max - min) + 1;
        int res = r.nextInt(l) + min;

        return res;
    }

    private static String getRandomDateOfBirth() {


        return getStringFromDate(LocalDate.now().minusYears(getRandomAge()));
    }


    private static String getAPhoneNumber() {
        String phone = getRandomPhoneNumber();
        while (generatedPhones.contains(phone) == true) {

            phone = getRandomPhoneNumber();
        }
        generatedPhones.add(phone);
        return phone;
    }


    public  static RegisterRequest createRegisterRequestWithRandomPinCode(String user) {

        return createRegisterRequestWith(user,getRandomPinCode());
    }
    public  static RegisterRequest createRegisterRequestWith(String user, int pincode) {
        RegisterRequest registerRequest = new RegisterRequest();
        String userNameinLowerCase = user.replace(" ", "").toLowerCase().replaceAll("[^a-z0-9]", "");
        ;
        registerRequest.setUserName(userNameinLowerCase);
        registerRequest.setPassword(PASSWORD);
        registerRequest.setFirstName(user);
        registerRequest.setLastName("");
        registerRequest.setGender(getRandomGender());
        registerRequest.setAddress(getRandomAddress(pincode));
        registerRequest.setPhoneNumber(getAPhoneNumber());
        registerRequest.setPinCode(pincode);
        registerRequest.setDateOfBirth(getRandomDateOfBirth());
        registerRequest.setEmail(userNameinLowerCase + "@upgrad.com");
        return registerRequest;
    }

}
