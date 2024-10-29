from elasticsearch import Elasticsearch, helpers
import json

# Kết nối với Elasticsearch
es = Elasticsearch("http://localhost:9200")
# Đọc dữ liệu từ file JSON
with open("C:/Users/luyen/Desktop/canifa_response_10000.json", "r", encoding="utf-8") as file:
    data = json.load(file)

# Lấy tất cả các document trong 'hits'
documents = data["hits"]["hits"]

# Đặt tên index đích
target_index = "vue_storefront_catalog_2_product_1729676856"

# Chuẩn bị các action để đẩy dữ liệu lên Elasticsearch
actions = [
    {
        "_op_type": "index",
        "_index": target_index,
        "_id": doc["_id"],  # giữ nguyên ID của document nếu muốn
        "_source": doc["_source"]
    }
    for doc in documents
]

# Đẩy dữ liệu lên index đích
try:
    helpers.bulk(es, actions)
    print("Đẩy dữ liệu thành công lên index:", target_index)
except Exception as e:
    print("Lỗi khi đẩy dữ liệu:", e)
