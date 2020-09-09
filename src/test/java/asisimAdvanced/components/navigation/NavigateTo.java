package asisimAdvanced.components.navigation;

import asisimAdvanced.pages.manage.doctors.ManageDoctorsPage;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import org.openqa.selenium.By;

import static asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage.page;

public class NavigateTo {
    public static class SoldierTreatment extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"טיפול בחייל\")]/parent::li");

        public static Runnable reception       = () -> init().clickSubOption(By.linkText("קליטת חייל"));
        public static Runnable newAppointment  = () -> init().clickSubOption(By.linkText("זימון תור"));
        public static Runnable newReference    = () -> init().clickSubOption(By.linkText("מתן הפנייה"));
        public static Runnable newPrescription = () -> init().clickSubOption(By.linkText("מתן מרשם"));

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

        public static Runnable appointmentList = () -> init().clickSubOption(By.linkText("רשימת התורים"));
        public static Runnable allAppointments = () -> init().clickSubOption(By.linkText("כל התורים"));

        private Appointments() {
            super(menuLocator);
        }

        private static Appointments init() {
            return new Appointments();
        }
    }

    public static class ClinicManagement extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"ניהול מרפאה\")]/parent::li");

        public static Runnable orderMedicine = () -> init().clickSubOption(By.linkText("קליטת תרופות"));
        public static Runnable statistics    = () -> init().clickSubOption(By.linkText("סטטיסטיקות"));

        private ClinicManagement() {
            super(menuLocator);
        }

        private static ClinicManagement init() {
            return new ClinicManagement();
        }
    }

    public static class BasisManagement extends MenuDropDown {
        private static By menuLocator = By.xpath("//*[contains(text(), \"ניהול תשתיות\")]/parent::li");

        public static ManageSoldiersPage soldiers() {
            init().clickSubOption(By.linkText("ניהול חיילים"));
            return page(ManageSoldiersPage.class);
        }
        public static ManageDoctorsPage doctors() {
            init().clickSubOption(By.linkText("ניהול רופאים"));
            return page(ManageDoctorsPage.class);
        }
        public static Runnable severities  = () -> init().clickSubOption(By.linkText("ניהול חומרת מחלות"));
        public static Runnable specialties = () -> init().clickSubOption(By.linkText("ניהול חיילים"));
        public static Runnable users       = () -> init().clickSubOption(By.linkText("ניהול משתמשים"));
        public static Runnable medicines   = () -> init().clickSubOption(By.linkText("ניהול תרופות"));

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
