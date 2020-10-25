package exams.bunker;

import java.util.Scanner;

public class Bunker {
    // Scanner definition
    static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        // Constant definition
        final int ACTION_INCOMING   = 1;
        final int ACTION_OUTGOING   = 2;
        final int SHIPMENT_AMMO     = 10;
        final int SHIPMENT_GRENADES = 20;
        final int SHIPMENT_SHELLS   = 30;
        final int FLAG_ACTIONS      = -1;
        final int FLAG_WITHDRAWAL   = -999;

        final String SHIPMENTS_MESSAGE =
                "\n[" + SHIPMENT_AMMO     + "] - Cartridges boxes" +
                "\n[" + SHIPMENT_GRENADES + "] - Grenades boxes" +
                "\n[" + SHIPMENT_SHELLS   + "] - Artillery shells";

        final String ACTIONS_MESSAGE =
                "\n[" + ACTION_INCOMING + "] - To deposit shipments" +
                "\n[" + ACTION_OUTGOING + "] - To withdraw shipments" +
                "\n[" + FLAG_ACTIONS    + "] - To stop";

        // Variable definition
        int actionCode;
        int numberOfShipments;
        int shipmentCode;
        int amountOfAmmoBoxesInBunker    = 0;
        int amountOfGrenadeBoxesInBunker = 0;
        int amountOfShellsInBunker       = 0;
        boolean canWithdrawFromBunker    = false;

        // Code section

        System.out.println("What would you like to do?" + ACTIONS_MESSAGE);
        actionCode = reader.nextInt();

        while (actionCode != FLAG_ACTIONS) {
            switch (actionCode) {
                case (ACTION_INCOMING): {
                    System.out.print("-- Shipment Deposit --\n" +
                                     "Enter the number of shipments: ");
                    numberOfShipments = reader.nextInt();

                    for (int i = 1; i <= numberOfShipments; i++) {
                        System.out.println("# " + i + ". Enter packet code:" + SHIPMENTS_MESSAGE);
                        shipmentCode = reader.nextInt();

                        switch (shipmentCode) {
                            case (SHIPMENT_AMMO): {
                                amountOfAmmoBoxesInBunker++;

                                break;
                            }
                            case (SHIPMENT_GRENADES): {
                                amountOfGrenadeBoxesInBunker++;

                                break;
                            }
                            case (SHIPMENT_SHELLS): {
                                amountOfShellsInBunker++;

                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }

                    break;
                }
                case (ACTION_OUTGOING): {
                    System.out.println("-- Shipment Withdrawal --\n"
                            + "Enter shipment code:"
                            + SHIPMENTS_MESSAGE
                            + "\n[" + FLAG_WITHDRAWAL + "] - To stop");
                    shipmentCode = reader.nextInt();

                    while (shipmentCode != FLAG_WITHDRAWAL) {
                        if (    (shipmentCode != SHIPMENT_AMMO) &&
                                (shipmentCode != SHIPMENT_GRENADES) &&
                                (shipmentCode != SHIPMENT_SHELLS)) {
                            System.out.println("Invalid shipment code.");
                        }
                        else {
                            System.out.print("Enter how many shipments to pull: ");
                            numberOfShipments = reader.nextInt();

                            switch (shipmentCode) {
                                case (SHIPMENT_AMMO): {
                                    if (amountOfAmmoBoxesInBunker >= numberOfShipments) {
                                        amountOfAmmoBoxesInBunker -= numberOfShipments;
                                        canWithdrawFromBunker = true;
                                    }

                                    break;
                                }
                                case (SHIPMENT_GRENADES): {
                                    if (amountOfGrenadeBoxesInBunker >= numberOfShipments) {
                                        amountOfGrenadeBoxesInBunker -= numberOfShipments;
                                        canWithdrawFromBunker = true;
                                    }

                                    break;
                                }
                                case (SHIPMENT_SHELLS): {
                                    if (amountOfShellsInBunker >= numberOfShipments) {
                                        amountOfShellsInBunker -= numberOfShipments;
                                        canWithdrawFromBunker = true;
                                    }

                                    break;
                                }
                                default: {
                                    System.out.println("Wrong Packet code");

                                    break;
                                }
                            }

                            if (canWithdrawFromBunker) {
                                System.out.println("Supplied " + numberOfShipments
                                        + " shipments of code " + shipmentCode);
                            }
                            else {
                                System.out.println("Could not supply " + numberOfShipments
                                        + " shipments of code " + shipmentCode);
                            }

                            canWithdrawFromBunker = false;
                        }

                        System.out.println("-- Outgoing Shipment --\n"
                                + "Enter shipment code:\n["
                                + SHIPMENT_AMMO     + "] - Ammunition boxes\n["
                                + SHIPMENT_GRENADES + "] - Grenade Boxes\n["
                                + SHIPMENT_SHELLS   + "] - Artillery shells\n["
                                + FLAG_WITHDRAWAL   + "] - To stop");
                        shipmentCode = reader.nextInt();
                    }

                    break;
                }
                default: {
                    System.out.println("Wrong input");

                    break;
                }
            }

            System.out.println("What would you like to do?\n["
                    + ACTION_INCOMING    + "] - To deposit shipments\n["
                    + ACTION_OUTGOING + "] - To withdraw shipments\n["
                    + FLAG_ACTIONS      + "] - To stop");
            actionCode = reader.nextInt();
        }

        System.out.println("The bunker contains:\n" +
                amountOfAmmoBoxesInBunker + " ammunition boxes\n"         +
                amountOfGrenadeBoxesInBunker + " grenade boxes\n"     +
                amountOfShellsInBunker        + " artillery shells");
    }
}