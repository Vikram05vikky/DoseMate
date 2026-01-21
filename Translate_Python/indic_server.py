from flask import Flask, request, jsonify
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
from indic_transliteration import sanscript
import torch

app = Flask(__name__)

MODEL_NAME = "ai4bharat/indictrans2-en-indic-dist-200M"

# ✅ Load tokenizer & model
tokenizer = AutoTokenizer.from_pretrained(
    MODEL_NAME,
    trust_remote_code=True
)

model = AutoModelForSeq2SeqLM.from_pretrained(
    MODEL_NAME,
    trust_remote_code=True
)

model.eval()  # 🔑 REQUIRED

LANG_MAP = {
    "tamil": "tam_Taml",
    "hindi": "hin_Deva",
    "telugu": "tel_Telu",
    "kannada": "kan_Knda",
    "malayalam": "mal_Mlym"
}

def devanagari_to_tamil(text):
    return sanscript.transliterate(
        text,
        sanscript.DEVANAGARI,
        sanscript.TAMIL
    )

@app.route("/translate", methods=["POST"])
def translate():
    data = request.json
    text = data.get("text", "")
    target_language = data.get("target_language", "").lower()

    if not text.strip() or target_language not in LANG_MAP:
        return jsonify({"translated_text": text})

    source_lang = "eng_Latn"
    target_lang = LANG_MAP[target_language]

    # ✅ REQUIRED IndicTrans2 format
    tagged_text = f"{source_lang} {target_lang} {text}"

    inputs = tokenizer(
        tagged_text,
        return_tensors="pt",
        padding=True,
        truncation=True
    )

    with torch.no_grad():
        output = model.generate(
            **inputs,
            max_length=256,
            use_cache=False,
            num_beams=1,
            do_sample=False
        )

    translated_text = tokenizer.batch_decode(
        output,
        skip_special_tokens=True
    )[0]

    # 🔁 Convert Devanagari → Tamil
    if target_language == "tamil":
        translated_text = devanagari_to_tamil(translated_text)

    return jsonify({"translated_text": translated_text})

if __name__ == "__main__":
    print("🚀 Starting IndicTrans translation server...")
    app.run(
        host="127.0.0.1",
        port=5000,
        debug=True,
        threaded=True,
        use_reloader=False
    )