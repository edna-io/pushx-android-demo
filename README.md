# edna push Demo project for Android
Гид по интеграции на русском <https://docs.edna.ru/kb/podkljuchenie-pushej-android/>

Guide in English <https://docs.edna.io/kb/enabling-android-push-notifications/>

https://edna.ru/

По вопросам продаж/Sales:
sales@edna.ru

Демо-приложение push-x представлено в нескольких вариантах инициализации (build variants):
- autoInit. Автоматическая инициализация, при которой edna_app_id указывается в манифесте приложения и инициализация проводится средствами библиотеки при запуске приложения – рекомендуемый вариант интеграции, который подходит для большинства случаев
- manualInit. Ручная инициализация, при которой edna_app_id передаётся в методе PushX.initialize(). Подробнее https://docs.edna.ru/kb/ruchnaya-inicializaciya-push-biblioteki-android/
- manualInitWithEdnaId. Ручная инициализация с ручным вводом идентификатора в интерфейсе демо-приложения – используйте только для тестирования, не подходит для прода!
  Если не знаете, какой способ инициализации вам подходит, проконсультируйтесь со службой поддержки support@edna.ru или со своим менеджером.

Перед сборкой демо-приложения выполните следующие шаги:
1. Укажите edna_app_id из личного кабинета edna.
- если используете вариант автоматической или ручной инициализации, укажите идентификатор edna_app_id в app/build.gradle (для всех вариантов, кроме manualInitWithEdnaId)
- если используйте вариант ручной инициализации с ручным вводом edna_app_id (вариант manualInitWithEdnaId), укажите переменную ednaAppId в файле app/src/manualInitWithEdnaId/java/com/edna/android/push/demo_x/app/PushXApplication.kt
2. Добавьте файл сервисов Google app/google-services.json из консоли Firebase. Подробнее о том, как получить файл https://docs.edna.ru/kb/kak-poluchit-google-services-json-v-firebase/
3. Добавьте файл сервисов Huawei app/agconnect-services.json, подробнее https://docs.edna.ru/kb/kak-zaregistrirovat-prilozhenie-v-huawei-app-gallery/
4. Укажите переменную Rustore projectId в следующих файлах:
- для автоматического варианта инициализации: app/src/autoInit/java/com/edna/android/push/demo_x/app/PushXApplication.kt
- для ручного варианта инициализации: app/src/manualInit/java/com/edna/android/push/demo_x/app/PushXApplication.kt
- для ручного варианта инициализации с ручным вводом edna_app_id: app/src/manualInitWithEdnaId/java/com/edna/android/push/demo_x/app/PushXApplication.kt

5. Убедитесь, что подпись приложения совпадает с той, которую вы указали при регистрации приложения в пуш-облаках Huawei и Rustore. Если подписи не совпадут, регистрация приложения в облаке не пройдет.

По любым вопросам обращайтесь в службу поддержки support@edna.ru или к своему менеджеру.