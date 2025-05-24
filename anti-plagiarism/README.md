Перед проверкой закрой весь chrome и подруби его вот так, а то у меня ругалось на CORS
```bash
open -na "Google Chrome" --args \
  --disable-web-security \
  --user-data-dir="/tmp/unsafe_chrome"
```

### Пример ответа analysis-service (ну оно типо работает)

```json
{
  "id": "b9706ba7-5c0a-4b87-8ee9-483fdda54d8e",
  "fileId": "7aeb014b-3859-4849-8ef0-80ebc7d37fb6",
  "topWords": {
    "антиплагиат": 2,
    "облака": 2,
    "балл": 4,
    "микросервисов": 4,
    "баллов": 2,
    "функциональность": 2,
    "хранение": 2,
    "file": 2,
    "реализация": 2,
    "разработать": 2,
    "отчетов": 3,
    "функциональности": 2,
    "балла": 3,
    "api": 5,
    "выдачу": 2,
    "веб": 3,
    "код": 2,
    "сдачи": 2,
    "анализа": 2,
    "включая": 2,
    "файлов": 2,
    "слов": 3,
    "service": 2,
    "только": 4,
    "документация": 3,
    "семинаристом": 2,
    "для": 3,
    "отвечает": 3,
    "приложения": 2,
    "кода": 3
  },
  "wordCloudUrl": "https://quickchart.io/wordcloud?format=png&width=600&height=400&fontScale=15&text=2:%D0%B0%D0%BD%D1%82%D0%B8%D0%BF%D0%BB%D0%B0%D0%B3%D0%B8%D0%B0%D1%82,2:%D0%BE%D0%B1%D0%BB%D0%B0%D0%BA%D0%B0,4:%D0%B1%D0%B0%D0%BB%D0%BB,4:%D0%BC%D0%B8%D0%BA%D1%80%D0%BE%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81%D0%BE%D0%B2,2:%D0%B1%D0%B0%D0%BB%D0%BB%D0%BE%D0%B2,2:%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D1%81%D1%82%D1%8C,2:%D1%85%D1%80%D0%B0%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5,2:file,2:%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F,2:%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D1%82%D1%8C,3:%D0%BE%D1%82%D1%87%D0%B5%D1%82%D0%BE%D0%B2,2:%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D1%81%D1%82%D0%B8,3:%D0%B1%D0%B0%D0%BB%D0%BB%D0%B0,5:api,2:%D0%B2%D1%8B%D0%B4%D0%B0%D1%87%D1%83,3:%D0%B2%D0%B5%D0%B1,2:%D0%BA%D0%BE%D0%B4,2:%D1%81%D0%B4%D0%B0%D1%87%D0%B8,2:%D0%B0%D0%BD%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0,2:%D0%B2%D0%BA%D0%BB%D1%8E%D1%87%D0%B0%D1%8F,2:%D1%84%D0%B0%D0%B9%D0%BB%D0%BE%D0%B2,3:%D1%81%D0%BB%D0%BE%D0%B2,2:service,4:%D1%82%D0%BE%D0%BB%D1%8C%D0%BA%D0%BE,3:%D0%B4%D0%BE%D0%BA%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D1%86%D0%B8%D1%8F,2:%D1%81%D0%B5%D0%BC%D0%B8%D0%BD%D0%B0%D1%80%D0%B8%D1%81%D1%82%D0%BE%D0%BC,3:%D0%B4%D0%BB%D1%8F,3:%D0%BE%D1%82%D0%B2%D0%B5%D1%87%D0%B0%D0%B5%D1%82,2:%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F,3:%D0%BA%D0%BE%D0%B4%D0%B0&weighted=true"
}
```
# А теперь то что я сделал или пересказ тз чатом гпт =)

# Краткая документация проекта

* **Микросервисная архитектура**
    - services/api-gateway – маршрутизация запросов.
    - services/file-storage-service – загрузка, скачивание, удаление файлов, хранение метаданных в PostgreSQL, сами файлы – в MinIO.
    - services/analysis-service – извлекает текст из PDF (Apache PDFBox), считает частоту слов, строит ссылку на облако слов через QuickChart и сохраняет результат в БД.

* **Облако слов**
    - энд-поинт `POST /analysis/{fileId}?top=N`
    - хранит top-N слов (jsonb) + URL PNG-облака в таблице `analysis_results`.

* **Событийная интеграция**
    - после загрузки файла `file-storage-service` публикует событие `file-uploaded` в Kafka;
    - `analysis-service` слушает эту тему и автоматически запускает анализ.

* **Swagger / OpenAPI**
    - springdoc-openapi в каждом сервисе (`/v3/api-docs`, `/swagger-ui.html`);
    - gateway агрегирует две спеки через массив `springdoc.swagger-ui.urls`.

* **Docker-окружение**
    - docker-compose поднимает:
        - 3 сервиса, 2 PostgreSQL (storage-db, analysis-db), MinIO, Kafka + ZooKeeper, Gateway.
    - профили:
        - **local** – сервисы слушают `localhost`;
        - **docker** – внутри сети обращаются по именам контейнеров (`file-storage`, `analysis`, `kafka`).

* **CORS**
    - глобальный конфиг `CorsGlobalConfig` (WebFlux) разрешает все источники – удобно для фронта.

* **Качество кода**
    - модули чётко разделены (`common`, `api-gateway`, `file-storage-service`, `analysis-service`);
    - Javadoc на публичных классах;
    - логирование через SLF4J + Logback.

* **Как запустить**
```bash
  docker-compose up --build
```
