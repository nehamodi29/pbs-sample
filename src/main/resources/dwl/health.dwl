%dw 2.0
output application/json
---
{
	ServiceName:"Workday",
	Status: if(attributes.statusCode  == 200) "Up" else "Down"

	
}