package fractalmatic;

/**
 * The app launcher - is a work around for multi-platform package.
 */
public class AppLauncher {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //System.setProperty("prism.verbose","true");
        System.setProperty("prism.forceGPU", "true");
        System.setProperty("prism.order", "d3d,sw");
        App.main(args);
    }
}
