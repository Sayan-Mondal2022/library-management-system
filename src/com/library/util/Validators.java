package com.library.util;

import java.util.Scanner;

public class Validators {
    private static final Scanner sc = new Scanner(System.in);

    private Validators(){}

    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());

                if (value <= 0) {
                    System.out.println("Value must be positive!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    public static int readDays(String message) {
        while (true) {
            int days = readInt(message);

            if (days > 365) {
                System.out.println("Too many days! Max allowed is 365.");
                continue;
            }

            return days;
        }
    }
}
