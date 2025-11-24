import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { TumorType } from './entities/TumorType.entity';
import { TumorTypeService } from './services/tumorType.service';
import { TumorTypeController } from './controllers/tumorType.controller';

@Module({
    imports: [
        TypeOrmModule.forFeature([TumorType])
    ],
    controllers: [TumorTypeController],
    providers: [TumorTypeService]
})
export class tumorTypeModule{}