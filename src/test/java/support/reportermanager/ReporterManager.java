package support.reportermanager;

import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.service.ExtentService;

import java.util.List;

public class ReporterManager {
    /**
     * Returns the reporter of type {@code reporterClass}
     * <br>reporter class from {@link ExtentService} or null
     * <br>if the reporter is not initialized.
     * @param reporterClass The class of t
     * @param <T>
     * @return
     */
    public static <T extends ExtentReporter> T getReporterByClass(Class<T> reporterClass) {
        List<ExtentReporter> reporters = ExtentService.getInstance().getStartedReporters();

        for (ExtentReporter reporter : reporters) {
            if (reporterClass.isInstance(reporter)) {
                return reporterClass.cast(reporter);
            }
        }

        return null;
    }
}
