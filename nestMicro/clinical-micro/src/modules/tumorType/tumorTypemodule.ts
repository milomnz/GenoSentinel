import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { TumorType } from './entities/TumorType.entity';
import { TumorTypeService } from './services/tumorType.service';

@Module({
    imports: [
        TypeOrmModule.forFeature([TumorType])
    ],
    controllers: [],
    providers: [TumorTypeService]
})
export class tumorTypeModule{}