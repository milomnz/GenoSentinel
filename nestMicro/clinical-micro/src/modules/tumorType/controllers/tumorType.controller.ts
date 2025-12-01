import {Controller, Get, Post, Body, Param, ParseIntPipe, HttpCode, HttpStatus} from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse, ApiParam, ApiOkResponse } from '@nestjs/swagger';
import { TumorTypeService } from '../services/tumorType.service';
import { CreateTumorTypeDto } from 'src/modules/tumorType/dto/create-tumorType';
import { TumorType } from 'src/modules/tumorType/entities/TumorType.entity';
import { stat } from 'fs';

@Controller('tumor-types')
export class TumorTypeController{
    constructor(private readonly tumorTypeService: TumorTypeService){}

    @Post()
    @HttpCode(HttpStatus.CREATED)
    @ApiOperation({
        summary: 'Registra un nuevo tipo de tumor',
        description: 'Crea una nueva clasificacion histológica con el sistema afectado.'
    })
    @ApiResponse({
        status: 201,
        description: 'El tipo de tumos ha sido creado exitosamente',
        type: TumorType
    })
    @ApiResponse({
        status: 400,
        description: 'Datos de entrada invalidos'
    })
    create(@Body() createTumorTypeDto: CreateTumorTypeDto): Promise <TumorType>{
        return this.tumorTypeService.create(createTumorTypeDto);
    }

    @Get()
    @ApiOperation({
        summary: 'Obtiene todos los tipos de tumor',
        description: ' Devuelve una lista completa de todas las clasificaciones de tumores registradas'
    })
    @ApiResponse({
        status: 200,
        description: 'Lista de tipos de tumores retornada exitosamente',
        type: [TumorType]
    })
    findAll(): Promise<TumorType[]>{
        return this.tumorTypeService.findAll();
    }

    @Get(':id')
    @ApiOperation({
        summary: 'Obtiene un tipo de tumor por ID',
        description: ' Busca y devuelve la clasificacion de tumor correspondiente al ID proporcionado'
    })
    @ApiParam({
        name: 'id',
        description: 'ID numerico del tipo de tumor',
        type: Number
    })
    @ApiResponse({
        status: 200,
        description: 'Tipo de tumor encontrado exitosamente.',
        type: TumorType
    })

    @ApiResponse({
        status: 404,
        description:'Tipo de tumor no encontrado'
    })
    findById(@Param('id', ParseIntPipe) id: number): Promise<TumorType>{
        return this.tumorTypeService.findById(id);
    }
<<<<<<< Updated upstream
=======

    @Patch(':id')
    @ApiOperation({
        summary: 'Actualizacion parcial de un tipo de tumor',
        description: 'Actualiza el nombre o el sistema afectado de un tipo de tumor existente.'
    })

    @ApiParam({
        name: 'id del tipo de tumor y DTO con los campos a actualizar',
        description: 'ID numerico del tipo de tumor',
        type: UpdateTumorTypeDto
    })

    @ApiResponse({
        status:200,
        description: 'Tipo de tumor actualizado exitosamente',
        type: TumorType
    })

    @ApiResponse({
        status:404,
        description: 'Tipo de tumor no encontrado'
    })

    updatePartial(
        @Param('id', ParseIntPipe) id:number,
        @Body()updateDto: UpdateTumorTypeDto): Promise<TumorType>{
            return this.tumorTypeService.updatePartial(id, updateDto);
        
    }

    @Delete(':id')
    @HttpCode(HttpStatus.NO_CONTENT)
    @ApiOperation({
        summary: 'Elimina un tipo de tumor',
        description: 'Elimina permanentemente un tipo de tumor por su ID'
    })

    @ApiParam({
        name: 'id',
        description: 'ID numerico del tipo de tumor a eliminar',
        type:Number
    })

    @ApiResponse({
        status: 204,
        description: 'Tipo de tumor no encontrado'
    })
    delete(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.tumorTypeService.delete(id);
    }
>>>>>>> Stashed changes
    
}