from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import uuid

app = FastAPI(title="가짜 PG 서버")

# 메모리 DB (서버 꺼지면 날아감)
payments_db = {}

class PrepareRequest (BaseModel):
  orderId: str
  amount: int

class AuthRequest(BaseModel):
  orderId: str
  amount: int

class ConfirmRequest(BaseModel):
  orderId: str
  amount: int
  authKey: str


# 결제 등록 (결제 서버에서 요청)
@app.post("/pg/prepare")
def prepare_payment(req: PrepareRequest):
  payments_db[req.orderId] = {
    "amount": req.amount,
    "status": "PREPARED",
    "authKey": None
  }

  return {"orderId": req.orderId, "status": "PREPARED"}


# 프론트에서 결제하고 authKey 받음
@app.post("/pg/auth")
def authenticate_payment(req: AuthRequest):
  print(req.orderId)
  payment = payments_db.get(req.orderId)

  # 사전 등록된 주문인지 체크
  if not payment:
    raise HTTPException(status_code=404, detail="사전에 등록되지 않은 주문입니다.")
  
  if payment["amount"] != req.amount:
    raise HTTPException(status_code=400, detail="결제 요청 금액이 위변조되었습니다.")
  
  auth_key = f"auth-{uuid.uuid4().hex[:8]}"
  payment["status"] = "COMPLETED"
  payment["authKey"] = auth_key

  return {"orderId": req.orderId, "authKey": auth_key, "status": "COMPLETED"}


# 결제서버에서 검증
@app.post("/pg/confirm")
def confirm_payment(req: ConfirmRequest):
  payment = payments_db.get(req.orderId)

  if not payment:
        raise HTTPException(status_code=404, detail="결제 내역을 찾을 수 없습니다.")
        
  # 프론트엔드가 가져온 인증 키가 PG사가 발급했던 키가 맞는지 교차 검증
  if payment["authKey"] != req.authKey:
      raise HTTPException(status_code=401, detail="유효하지 않은 인증 키입니다.")
      
  # 최종 승인 요청 금액 검증
  if payment["amount"] != req.amount:
      raise HTTPException(status_code=400, detail="최종 승인 금액이 일치하지 않습니다.")
  
  # 어차피 결제 완료가 안 되었으면 authKey 검증에서 막힐 것임
  if payment["status"] != "COMPLETED":
     raise HTTPException(status_code=403, detail="결제 완료된 건이 아닙니다.")
  
  payment["status"] = "CONFIRMED"

  return {"orderId": req.orderId, "amount": payment["amount"], "status": payment["status"], 
          "receiptUrl": f"https://fake-pg.com/receipt/{req.orderId}"}