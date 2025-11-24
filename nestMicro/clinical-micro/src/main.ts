import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';


async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  // Swagger setup 
  const config = new DocumentBuilder()
    .setTitle('Clinical Microservice API')
    .setDescription('Documentacion del micro servicio clinico de GenoSentinel')
    .setVersion('1.0')
    .build();

  const document = SwaggerModule.createDocument(app as any, config);
  SwaggerModule.setup('api-docs', app as any, document);
  await app.listen(process.env.PORT ?? 3000);
}
bootstrap();