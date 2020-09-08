package asisimAdvanced.navigation;

import org.openqa.selenium.By;

public class NavigateTo {
    public static class SoldierTreatment extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"טיפול בחייל\")]/parent::li");

        public static Runnable reception       = () -> init().openSubMenu(By.linkText("קליטת חייל"));
        public static Runnable newAppointment  = () -> init().openSubMenu(By.linkText("זימון תור"));
        public static Runnable newReference    = () -> init().openSubMenu(By.linkText("מתן הפנייה"));
        public static Runnable newPrescription = () -> init().openSubMenu(By.linkText("מתן מרשם"));

        private SoldierTreatment() {
            super(menuLocator);
        }

        private static SoldierTreatment init() {
            return new SoldierTreatment();
        }
    }

    public static class References extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"הפניות\")/parent::li");

        public static void get() {
            init().open();
        }

        private References() {
            super(menuLocator);
        }

        private static References init() {
            return new References();
        }
    }

    public static class Prescriptions extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"מרשמים\")]/parent::li");

        public static void get() {
            init().open();
        }

        private Prescriptions() {
            super(menuLocator);
        }

        private static Prescriptions init() {
            return new Prescriptions();
        }
    }

    public static class Appointments extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"תורים\")]/parent::li");

        public static Runnable appointmentList = () -> init().openSubMenu(By.linkText("רשימת התורים"));
        public static Runnable allAppointments = () -> init().openSubMenu(By.linkText("כל התורים"));

        private Appointments() {
            super(menuLocator);
        }

        private static Appointments init() {
            return new Appointments();
        }
    }

    public static class ClinicManagement extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"ניהול מרפאה\")]/parent::li");

        public static Runnable orderMedicine = () -> init().openSubMenu(By.linkText("קליטת תרופות"));
        public static Runnable statistics    = () -> init().openSubMenu(By.linkText("סטטיסטיקות"));

        private ClinicManagement() {
            super(menuLocator);
        }

        private static ClinicManagement init() {
            return new ClinicManagement();
        }
    }

    public static class BasisManagement extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[@id = \"BasisManagement\"]/parent::li");

        public static Runnable soldiers    = () -> init().openSubMenu(By.linkText("ניהול חיילים"));
        public static Runnable doctors     = () -> init().openSubMenu(By.linkText("ניהול רופאים"));
        public static Runnable severities  = () -> init().openSubMenu(By.linkText("ניהול חומרת מחלות"));
        public static Runnable specialties = () -> init().openSubMenu(By.linkText("ניהול חיילים"));
        public static Runnable users       = () -> init().openSubMenu(By.linkText("ניהול משתמשים"));
        public static Runnable medicines   = () -> init().openSubMenu(By.linkText("ניהול תרופות"));

        private BasisManagement() {
            super(menuLocator);
        }

        private static BasisManagement init() {
            return new BasisManagement();
        }
    }

    public static class Logout extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"יציאה\")]/parent::li");

        public static void get() {
            init().open();
        }

        private Logout() {
            super(menuLocator);
        }

        private static Logout init() {
            return new Logout();
        }
    }
}
