// Demonstrates usage of the new udpServer feature.
//You can register the same function to multiple ports, and multiple functions to the same port.
//
// 2013-4-7 Brian Lee <cybexsoft@hotmail.com>

#include <EtherCard.h>
#include <IPAddress.h>
#include <MQ2.h>
#include "max6675.h"

void variarNome();

#define load_Res 10
#define sensorLuz A0
#define sensorFunmaca A1
#define SS 10


//MAX6675(int8_t SCLK, int8_t CS, int8_t MISO);
MAX6675 thermocouple(6, 5, 4);

MQ2 mq2(sensorFunmaca);


// ethernet mac address - must be unique on your network
static byte mymac[] = { 0x74, 0x69, 0x69, 0x30, 0x2D, 0x33 };
// ethernet interface ip address
static byte myip[] = { 10, 0, 0, 101 };
// gateway ip address
static byte gwip[] = { 10, 0, 0, 1 };
// mask
static byte mask[] = { 255, 255, 255, 0 };
//dns
static byte dns[] = { 10, 0, 0, 1 };

byte Ethernet::buffer[256]; // tcp/ip send and receive buffer

static byte ipServe[4] = { 10, 0, 0, 100 };
char nome[20];
char mensage[100];
char auxChar[10];
unsigned int portServe = 2000;
static uint32_t timer;
double PPMFunmaca;
char dados;


bool recebido = false;
//bool udpSerialPrint(word port, byte ip[4], const char *data, word len) {
void udpSerialPrint(word port, byte ip[4], char *data, word len) {
  IPAddress src(ip[0], ip[1], ip[2], ip[3]);
  recebido = true;
}

void setup() {
  strcpy(nome,"Cozinha");
//==================================================== Serial
  
  Serial.begin(9600);
  Serial.println("Serial iniciado");

//=================================================== Placa de Rede

  Serial.print("Placa de rede iniciando...");
  if (ether.begin(sizeof Ethernet::buffer, mymac,SS) == 0){
    Serial.println( "Failed to access Ethernet controller");
  }else{
    Serial.println("Placa de rede iniciado");  
  }

//======================== Configurando IP
    
  Serial.println("ip Statico definido");
  //ether.staticSetup(myip, gwip,dns,mask);
  ether.staticSetup(myip,gwip,dns,mask);

  ether.copyIp(ether.hisip,ipServe);

  Serial.println("Ip's");
  ether.printIp("IP:  ", ether.myip);
  ether.printIp("GW:  ", ether.gwip);
  ether.printIp("DNS: ", ether.dnsip);
  ether.printIp("SRV: ", ether.hisip);
  ether.printIp("MAC: ", ether.mymac);

  ether.udpServerListenOnPort(&udpSerialPrint, portServe);
  strcpy(mensage,"CONECT::");
  int TRUE = 1;
  while(TRUE){
      ether.sendUdp(mensage, sizeof(mensage), portServe, ipServe, portServe);
      timer = millis() + 5000;
      Serial.println("Aguardando resposta!"); 
      while(millis() < timer){
          ether.packetLoop(ether.packetReceive());
          if(recebido){
              Serial.println("Conexão aceita!");
              TRUE = 0;
              recebido = false;
              break;
          }
      }
  }
  
//==================================================== Luz

  pinMode(sensorLuz, INPUT);
  Serial.println("Luz"+analogRead(sensorLuz));

//==================================================== Fumaça

  Serial.println("Calibrando.....");
  mq2.begin();
  //PPMFunmaca = resistencia(50,500)/air_factor;
  //Serial.print("PPM: ");    
  //Serial.println(PPMFunmaca);                                                                                      
  Serial.println("Calibrado.");

  
  Serial.println("\nFim das configurações\n");
}


  float cont_leitura = 0.0;
  float media_temp = 0.0, media_luz = 0.0, media_fum = 0.0;
  
  void loop() {
    if (millis() > timer) {
      timer = millis() + 300;
      strcpy(mensage,"MSG::NAME::");
      strcat(mensage,nome);
      strcat(mensage,"TEMP::");
      dtostrf((media_temp/cont_leitura),6,2,auxChar);
      strcat(mensage,auxChar);
      strcat(mensage,"LUZ::");
      dtostrf((media_luz/cont_leitura),8,2,auxChar);
      strcat(mensage,auxChar);
      strcat(mensage,"FUM::");
      dtostrf((media_fum/cont_leitura),6,2,auxChar);
      strcat(mensage,auxChar);
      strcat(mensage,"END\0");
      Serial.println(mensage);
       
      //static void sendUdp (char *data,uint8_t len,uint16_t sport, uint8_t *dip, uint16_t dport);
      ether.sendUdp(mensage, sizeof(mensage), portServe, ipServe, portServe );
      for(int i =0;i<100;i++){
        mensage[i] = '\0';
      }
      cont_leitura = 0.0;
      media_temp = 0.0;
      media_luz = 0.0;
      media_fum = 0.0;
      
    }else{
      cont_leitura++;
      media_temp =+ thermocouple.readCelsius();
      media_luz =+ Luz(5,80);
      media_fum =+ mq2.readSmoke();
    }
  }


double Luz(int num_leituras, int tempo){
   int i;
   double res=0; 
   for (i=0;i<num_leituras;i++) 
   {
      int leitura=analogRead(sensorLuz);
      res+=(double)(1023-leitura);
      delay(tempo);
   }
   res/=num_leituras;
   return res;
}

/*
int cont = 0;

void variarNome(){
   switch(cont){
      case 0:
       strcpy(nome,"Lavanderia");
        cont ++;
      break;
      case 1:
        strcpy(nome,"Corredor Norte01");
        cont ++;
      break;
      case 2:
        strcpy(nome,"Quarto 101");
        cont ++;
      break;
      case 3:
        strcpy(nome,"Cozinha");
        cont = 0;
      break;
   }
}
*/
