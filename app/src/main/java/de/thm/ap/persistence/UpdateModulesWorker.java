package de.thm.ap.persistence;

public class UpdateModulesWorker extends Worker {
    private static final
    String MODULES_URL ="https://homepages.thm.de/~hg10187/modules.json";
    public Result doWork() {
        SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("modules", Context.MODE_PRIVATE);
        HttpURLConnection connection = null;
        InputStream in = null;
        try {
            connection = (HttpURLConnection) new URL(MODULES_URL).openConnection();
            connection.setIfModifiedSince(sharedPrefs.getLong("lastModified",0));
            if (connection.getResponseCode() ==200) {

                in = connection.getInputStream();
                Module[] modules =
                        new
                                Gson().fromJson(
                                new
                                        InputStreamReader(in), Module[].
                                        class
                        );
                moduleDAO.deleteAll();
                moduleDAO.persistAll(modules);
                sharedPrefs.edit()
                        .putLong(
                                "lastModified"
                                , connection.getLastModified())
                        .apply();
            }
        }
        catch
                (IOException e) {
            e.printStackTrace();
            return
                    Result.
                            FAILURE
                    ;
        }
        finally
        {
            IOUtils.
                    closeQuietly
                            (in);
            if
                    (connection !=
                    null
                    ) {
                connection.disconnect();
            }
        }
        return
                Result.
                        SUCCESS
                ;
    }
}