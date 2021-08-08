package game;


import java.util.logging.Logger;

/**
 * Java Process API Utils.
 * Java version >= 8, Windows version >= 6.0
 */
public final class ProcessUtils {

    private static final Logger LOG = Logger.getLogger(ProcessUtils.class.getName());

    private static final String WMIC_WORKING_SET_SIZE_HEAD = "wmic process where ProcessID=";
    private static final String WMIC_WORKING_SET_SIZE_TAIL = " get WorkingSetSize";
    private static final int WORKING_SET_SIZE_VALUE_INDEX = 2;
    public static final String IBM_866 = "IBM866";
    public static final String UTF_8 = "utf-8";

    private ProcessUtils() {
        //Utility
    }

//    /**
//     * Get java.lang.Process id.
//     *
//     * @param process process
//     * @return process id
//     */
//    @SuppressWarnings({"squid:S1872", "squid:S1191",
//            "fb-contrib:RFI_SET_ACCESSIBLE", "squid:S3776", "pmd:SimplifyStartsWith"})
//    public static int getPid(Process process) {
//        int pid = 0;
//        String javaVersion = getJavaVersion();
//        try {
//            if(javaVersion.startsWith("9") || javaVersion.startsWith("10")
//                    || javaVersion.startsWith("11") || javaVersion.startsWith("12")) {
//                if ("java.lang.ProcessImpl".equals(process.getClass().getName())) {
//                    Field f = process.getClass().getDeclaredField("pid");
//                    f.setAccessible(true);
//                    pid = f.getInt(process);
//                    f.setAccessible(false);
//                }
//            } else {
//                if (isWindows()) {
//                    if ("java.lang.Win32Process".equals(process.getClass().getName()) ||
//                            "java.lang.ProcessImpl".equals(process.getClass().getName())) {
//                        Field f = process.getClass().getDeclaredField("handle");
//                        f.setAccessible(true);
//                        long hl = f.getLong(process);
//                        com.sun.jna.platform.win32.WinNT.HANDLE handle = new com.sun.jna.platform.win32.WinNT.HANDLE();
//                        handle.setPointer(com.sun.jna.Pointer.createConstant(hl));
//                        pid = com.sun.jna.platform.win32.Kernel32.INSTANCE.GetProcessId(handle);
//                        f.setAccessible(false);
//                    }
//                } else if(isLinux()){
//                    if ("java.lang.UNIXProcess".equals(process.getClass().getName())) {
//                        Field f = process.getClass().getDeclaredField("pid");
//                        f.setAccessible(true);
//                        pid = f.getInt(process);
//                        f.setAccessible(false);
//                    }
//                }
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            LOG.log(Level.SEVERE, "Error getting process ID", e);
//        }
//        return pid;
//    }

//    /**
//     * Is process WorkingSetSize/VM Resident Set Size within limit?
//     * @param pid process ID
//     * @param limit limit
//     * @return is?
//     */
//    @SuppressWarnings("squid:S00108")
//    public static boolean isWorkingSetSizeWithinLimits(int pid, int limit){
//        if (isWindows() && isWmicWindows()){
//            List<String> out = new ArrayList<>();
//            String cmd = WMIC_WORKING_SET_SIZE_HEAD + pid + WMIC_WORKING_SET_SIZE_TAIL;
//            try {
//                Process p = Runtime.getRuntime().exec(cmd);
//                if(p != null){
//                    out = inputStreamToListOfStrings(p.getInputStream());
//                    if(out.size() > WORKING_SET_SIZE_VALUE_INDEX) {
//                        int actual = Integer.parseInt(out.get(WORKING_SET_SIZE_VALUE_INDEX).trim());
//                        return actual <= limit;
//                    }
//                }
//            } catch (IOException e) {
//            }
//        } else if(isLinux()){
//            Path path = Paths.get("/proc/" + pid + "/status");
//            if(path.toFile().exists()){
//                String vmRSSString = readPropertiesFile(path).getProperty("VmRSS");
//                int sizeInKb = Integer.parseInt(vmRSSString.replaceAll("\\D+",""));
//                int limitInKb = limit / 1024;
//                if(sizeInKb > limitInKb){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
}
